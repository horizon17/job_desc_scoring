package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * location
 * salary
 *
 * graph: horiz - score, vert - salary, size - same
 *
 */
@Component
public class MainComponent {

    @Autowired
    private EMLReader emlReader;

    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private ScoreCalculation scoreCalculation;

    @Autowired
    private SalaryAnalyzer salaryAnalyzer;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    LocationAnalyzer locationAnalyzer;

    @Autowired
    FileUtility fileUtility;

    public final Map<String, String> sharedMap = Collections.synchronizedMap(new HashMap<>());

    final String tempPath = "c:\\temp\\ab81_and_dev\\";
    final String allEmlPath = "c:\\temp\\all_eml_files\\";
    final Path destinationDir = Paths.get(allEmlPath);

    public void run() throws CsvValidationException, Exception, URISyntaxException {

        // todo - to start param
        boolean coolPrint = false;

        //Integer fileCount = findMaxFile();
        //List<Path> pathList = getAllFiles("messages");

        // move files with new names, delete old
        List<Path> sourceFileList = getAllFiles(tempPath);
        String newFileName = UUID.randomUUID() + ".eml";
        for (Path path : sourceFileList) {
            fileUtility.copyAndDelete(path, destinationDir, newFileName);
        }

        // read all files
        List<Path> pathList = getAllFiles(allEmlPath);

        List<EmailDTO> emailDTOS = new ArrayList<>();
        for (Path path : pathList) {
            if (!path.getFileName().toString().contains(".eml")) {
                continue;
            }
            //String fileName = tempPath + path.getFileName();
            //jdReader.readCSV(fileName, emailDTOS);
            EmailDTO emailDTO = emlReader.readFileByName(path.toString());
            if (emailDTO == null) {
                continue;
            }
            emailDTOS.add(emailDTO);
            if (emailDTOS.size() == 0) {
                System.out.println("no emails");
                return;
            }
        }

        List<JoblVO> joblVOS = new ArrayList<>();

        for (EmailDTO emailDTO : emailDTOS) {
            String sbj = emailDTO.getSubject().toLowerCase();
            String from = emailDTO.getFrom().toLowerCase();
            if (sbj.contains("rate confirmation") // todo list of exclusion
                    || sbj.contains("rtr")
                    || sbj.contains("re: salary confirmation")
                    || sbj.contains("hotlist")
                    || sbj.contains("rentals")
                    || sbj.contains("monthly statement")
                    || sbj.contains("security alert")
                    || sbj.contains("invitations and notifications")
                    || sbj.contains("thanks for applying to")
                    || sbj.contains("just messaged you")
                    || sbj.contains("right to represent")
                    || sbj.contains("want to connect")
                    || sbj.contains("who opens your emails")
                    || sbj.contains("otter.ai")
                    || sbj.contains("intro apr")
                    || sbj.contains("nerdwallet")
                    || sbj.contains("zillow")
                    || from.contains("nerdwallet")
                    || from.contains("otter")
                    || (sbj.contains("RE:") && !sbj.contains("Hire"))) {
                System.out.println("skipped with subject " + emailDTO.getSubject());
                continue;
            }
            JoblVO joblVO = handlerDto(emailDTO);
            joblVOS.add(joblVO);
        }
        joblVOS = joblVOS.stream()
                .sorted(Comparator.comparing(JoblVO::getScore).reversed())
                .collect(Collectors.toList());

        //  { x: 63.4, y: 51.8, z: 15.4, name: 'PT', country: 'Portugal' },
        //  { x: 64, y: 82.9, z: 31.3, name: 'NZ', country: 'New Zealand' }
        int i = 0;
        int kRandInit = -500000;
        int kMaxLevel = 10000;
        int kStep = 40000;
        int kShift = kRandInit;

        StringBuilder bottomComment = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        addPoint(sb, 0, 350000, 30, "test", "test", "11");
        addPoint(sb, 0, 300000, getSize("Remote"), "Remote", "Remote", "1");
        addPoint(sb, 0, 100000, getSize("OnSite"), "OnSite","OnSite", "2");
        addPoint(sb, 0, -100000, getSize("Hybrid"), "Hybrid","Hybrid", "3");
        addPoint(sb, 0, -300000, getSize("Any"), "Any",  "Remote or OnSite or Hybrid", "4");

        if (coolPrint) {
            addPoint(sb, 275, 175000, getSize(null), "CA", "Hybrid", "f2dd1eb8-e979-4ec8-8be9-431ab6380bea");
        }

        for (JoblVO joblVO : joblVOS) {
            if (joblVO.getScore() < 50 || (joblVO.getScore() < 90 && joblVO.getLoc().isEmpty())) {
                continue;
            }
            Integer salary = joblVO.getSalary();
            if (salary == 0) {
                salary =  kShift; // new Random().nextInt(20000) +

                kShift = kShift + kStep;
                if (kShift > kMaxLevel) {
                    kShift = kRandInit;
                }
                joblVO.setSalary(salary);
            }
            if (coolPrint) {
                if (joblVO.getLoc().isEmpty()) {
                    String[] randomLoc = new String[] {"CA", "FL", "TX", "OH"};
                    Integer rand = new Random().nextInt(randomLoc.length);
                    joblVO.setLoc(randomLoc[rand]);
                }
            }

            // point + tooltip output
            sb.append("{ x: " + joblVO.getScore() + ", "
                    + "y: " + salary + ", "
                    + "z: " + getSize(joblVO.getType()) + ", "
                    + "color: "+ getColor(joblVO.getType()) +","
                    + " loc: '" + joblVO.getLoc() + "', "
                    + " type: '" + joblVO.getType() + "', "
                    + " subj: '" + joblVO.getSubject() + "', "
                    + " from: '" + textProcessor.encodeForHtml(joblVO.getFrom()) + "', "
                    + " to: '" + joblVO.getTo() + "', "
                    + " uuid: '" + joblVO.getGuid() + "'}" + "\n");
            if (i != joblVOS.size() - 1) {
                sb.append(",");
            }

            bottomComment.append(" \n\n<br>");
            bottomComment.append(getColorHtml(joblVO.getType()));
            bottomComment.append(joblVO.getGuid());
            bottomComment.append(", score: ");
            bottomComment.append(joblVO.getScore());
            bottomComment.append(", salary: ");
            bottomComment.append(joblVO.getSalary());
            bottomComment.append(", // type: ");
            bottomComment.append(joblVO.getType());
            bottomComment.append(", // subj: ");
            bottomComment.append(joblVO.getSubject());
            bottomComment.append(", from: ");
            bottomComment.append(joblVO.getFrom());
            bottomComment.append(", to: ");
            bottomComment.append(joblVO.getTo());
            bottomComment.append(", date: ");
            bottomComment.append(joblVO.getDate());
            bottomComment.append(" \n\n<br>");
            bottomComment.append("----------");
        }

        //
        Resource resource = resourceLoader.getResource("classpath:static/index.html");
        if (resource.exists()) {
            URL url = resource.getURL();
            List<String> fileRowsList = getFromFile(url);

            String chartFile= "c:\\temp\\index.html";

            try (FileWriter writer = new FileWriter(chartFile)) {
                for (String line : fileRowsList) {
                    if (line.contains("replace_me")) {
                        writer.write(sb + System.lineSeparator());
                        continue;
                    }
                    writer.write(line + System.lineSeparator());
                }
                writer.write(bottomComment.toString());
            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }

        joblVOS.stream()
                .map(jd -> jd.getGuid() + " " + jd.getSubject()
                        + " (score " + jd.getScore()
                        + " salary ===> " + jd.getSalary()
                        + " location: ---> " + jd.getLoc())
                        //+ " date " + jd.getDate() + ")")
                .forEach(System.out::println);

    }

    public void addPoint(StringBuilder sb,
                         int x, int y, int z,
                         String location,
                         String type,
                         String uuid) {

        sb.append("{ x: " + x
                + ", y: " + y
                + ", z: " + z
                + ", color: " +  getColor(type)
                + ", loc: '" + location
                + "', type: '" + type
                + "', uuid: '" + uuid + "' },");
    }

    public JoblVO handlerDto(final EmailDTO emailDTO) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitText(emailDTO.getWholeBody().toLowerCase()));

        ObjectMapper om = new ObjectMapper();
        JoblVO joblVO = om.convertValue(emailDTO, JoblVO.class);
        joblVO.setSubject(textProcessor.encodeForHtml(emailDTO.getSubject()));

        // guid from file name
        List<String> fileList = Arrays.asList(emailDTO.getFileName().split("\\\\"));
        if (fileList.size() == 0) {
            return null;
        }
        String fileGUID = fileList.get(fileList.size() - 1).replace(".eml", "");
        joblVO.setGuid(fileGUID);

        sharedMap.put(joblVO.getGuid(), joblVO.getFileName());

        Integer score = scoreCalculation.doCalculation(jobDescList);
        joblVO.setScore(score);

        Integer salary = salaryAnalyzer.searchSalary(emailDTO.getWholeBody());
        joblVO.setSalary(salary);

        joblVO.setType(workType(emailDTO.getWholeBody().toLowerCase()));

        joblVO.setLocation(locationAnalyzer.searchLocation(emailDTO.getWholeBody()));
        joblVO.setLoc(locationAnalyzer.shortLocation(joblVO.getLocation()));

        if (joblVO.getType().contains("or")) {
            joblVO.setLoc(joblVO.getLoc() + " " + joblVO.getType());
        }

        return joblVO;
    }

    private List<String> getFromFile(URL url) throws IOException, URISyntaxException {

        return  Files.readAllLines(Path.of(url.toURI()));

    }

    private String workType(final String body) {
        StringBuilder sb = new StringBuilder();
        if (body.contains("jobs")) {
            return sb.append("Jobs list").toString();
        }
        if (body.contains("remote")) {
            sb.append("Remote");
        }
        if (body.contains("onsite")) {
            if (sb.length() != 0) {
                sb.append(" or ");
            }
            sb.append("OnSite");
        }
        if (body.contains("hybrid")) {
            if (sb.length() != 0) {
                sb.append(" or ");
            }
            sb.append("Hybrid");
        }
        return sb.toString();
    }

    private String getColor(final String type) {
        if (type.equals("Jobs list")) {
            return "'#070A73'";
        }
        if (type.equals("Remote")) {
            return "'#00FFFF'";
        }
        if (type.equals("OnSite")) {
            return "'#0066ff'";
        }
        if (type.equals("Hybrid")) {
            return "'#ff9933'";
        }
        return "'#b3ffb3'";
    }

    private String getColorHtml(String type) {
        if (type.equals("Jobs list")) {
            return "<div class=\"circleL\"></div>";
        }
        if (type.equals("Remote")) {
            return "<div class=\"circleR\"></div>";
        }
        if (type.equals("OnSite")) {
            return "<div class=\"circleS\"></div>";
        }
        if (type.equals("Hybrid")) {
            return "<div class=\"circleH\"></div>";
        }
        return "'#b3ffb3'";
    }


    private int getSize(final String type) {
        int defaultSize = 13;
        if (type == null) {
            return defaultSize;
        }
        if (type.equals("Jobs list")) {
            return 10;
        }
        if (type.equals("Remote")) {
            return 16;
        }
        if (type.equals("Hybrid")) {
            return 15;
        }
        if (type.equals("OnSite")) {
            return 14;
        }

        return defaultSize;
    }

    public Integer findMaxFile() throws IOException {
        Path path = Paths.get(tempPath);
        Optional<Integer> res;
        try (Stream<Path> walk = Files.walk(path)) {
            //List<String> walkList = walk.map(s->s.getFileName().toString()).collect(Collectors.toList());
            res = walk.filter(r->r.getFileName().toString().contains("messages-"))
                    .map(r->r.getFileName().toString().replaceAll("messages-|.csv", ""))
                    .map(e->Integer.parseInt(e))
                    .max(Comparator.comparingInt(i->i));
        }
        return res.get();
    }

    public List<Path> getAllFiles(String currentPath, String fileName) throws IOException {
        Path path = Paths.get(currentPath);
        List<Path> pathList;
        try (Stream<Path> walk = Files.walk(path)) {
            pathList= walk.filter(r->r.getFileName().toString().contains(fileName))
                    .collect(Collectors.toList());
        }
        return pathList;
    }

    public List<Path> getAllFiles(String currentPath) throws IOException {
        Path path = Paths.get(currentPath);
        List<Path> pathList = new ArrayList<>();
        if (!isDirectoryNotEmpty(path)) {
            return pathList;
        }
        try (Stream<Path> walk = Files.walk(path)) {
            pathList= walk.collect(Collectors.toList());
        }
        return pathList;
    }

    private static boolean isDirectoryNotEmpty(Path path) {
        if (Files.isDirectory(path)) {
            try (var dirStream = Files.newDirectoryStream(path)) {
                return dirStream.iterator().hasNext();
            } catch (IOException e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }
        return false;
    }

}
