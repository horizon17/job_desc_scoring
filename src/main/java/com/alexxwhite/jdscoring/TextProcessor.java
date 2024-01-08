package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * location
 * salary
 *
 * graph: horiz - score, vert - salary, size - same
 *
 */
@Component
public class TextProcessor {

    public List<String> prepareJD(List<String> jdSource) {

        jdSource = jdSource.stream()
                .map(r->r.replace("(", ""))
                .map(r->r.replace(")", ""))
                .map(r->encodeForHtml(r))
                .collect(Collectors.toList());

        jdSource.removeIf(e->e.isEmpty() || e == null || e.length() == 1);

        return jdSource;
    }

    public List<String> splitText(final String bitText) {
        return Arrays.asList(bitText.split("\\s*[/,;:.\n]\\s*"));
    }

    public List<String> splitTextByLines(final String bitText) {
        return Arrays.asList(bitText.split("\\s*[\r?\n]\\s*"));
    }

    public String encodeForHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace("\\", "&#92;")
                .replace("/", "&#47;");
    }

}
