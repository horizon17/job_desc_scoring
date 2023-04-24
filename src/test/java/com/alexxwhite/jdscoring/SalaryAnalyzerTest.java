package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class SalaryAnalyzerTest {

    @Autowired
    SalaryAnalyzer salaryAnalyzer;

    @Autowired
    MainComponent mainComponent;

    @Autowired
    private TextProcessor textProcessor;

    String jd = "\"Subject:\n" +
            "Software Engineer - Backend at 0x in San Francisco, CA and 19 more new jobs\n" +
            "From:\n" +
            "\"\"Your Indeed Job Feed\"\" <alert@indeed.com>\n" +
            "Date:\n" +
            "4/20/2023, 11:15 AM\n" +
            "To:\n" +
            "<anatoliibelov81@gmail.com>\n" +
            "\n" +
            "Indeed Daily Job Feed Email\n" +
            "See opportunities at JPMorgan Chase & Co, Peraton and Worldwide Express\n" +
            " \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \n" +
            "Indeed homepage\tsee messages\tsee notifications\n" +
            "Your job feed for\n" +
            "April 20, 2023\n" +
            "\n" +
            "Jobs are based on your preferences, profile, and activity on Indeed ¹\n" +
            " \n" +
            "Software Engineer - Backend\n" +
            "0x - San Francisco, CA\n" +
            "$95,000 - $230,000 a year\n" +
            "Collaborate cross-functionally with other teams, including engineering (internal and external), research, data science, product management, executive leadership…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Software Engineer - Backend Distributed Systems\n" +
            "New Relic  3.3 3.3/5 rating - Los Angeles, CA\n" +
            "$146,000 - $183,000 a year\n" +
            "This high-impact engineering position is a phenomenal opportunity to own and drive a huge growth area for the company. BS/MS/PhD in CS (or equivalent).\n" +
            "Just posted\n" +
            "Senior Java Engineer (Temp)\n" +
            "Worldwide Express  3.7 3.7/5 rating - Remote\n" +
            "Bachelor’s Degree in Computer Science or relevant field. Assist in the development of technical requirements for Agile development teams.\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Backend Software Engineer, Content & Client Services\n" +
            "Tubi - San Francisco, CA\n" +
            "Participate in the architecture, design and development of high-quality software solutions while ensuring the maintainability, scalability, and reliability of…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Software Engineer, Backend\n" +
            "Graytitude - Remote\n" +
            "$80,000 - $110,000 a year\n" +
            "BS/MS in Computer Science, related technical field or equivalent experience. Accept and give meaningful feedback on technical designs and implementations.\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "DSS Software Engineer\n" +
            "Qualitest  3.5 3.5/5 rating - Redmond, WA\n" +
            "$130,000 - $175,000 a year\n" +
            "Research, design, and implement tools and services around build and test systems along with E2E integration. Experience with front end development using popular…\n" +
            "Just posted\n" +
            "Senior .NET Software Engineer\n" +
            "Braintrust  4.5 4.5/5 rating - San Francisco, CA\n" +
            "$75 an hour\n" +
            "Coupling technological innovation with strategic partnerships, the client offers technology for businesses and individuals to expand their own portfolio into…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "DSS Software Engineer\n" +
            "QA InfoTech - Redmond, WA\n" +
            "$130,000 - $175,000 a year\n" +
            "Research, design, and implement tools and services around build and test systems along with E2E integration. Experience with front end development using popular…\n" +
            "Just posted\n" +
            "Staff Java Software Engineer\n" +
            "Inventech info Solutions - Remote\n" +
            "$120,000 - $150,000 a year\n" +
            "MongoDB and/or other NoSQL exp Knowledge of Java platform (collections, concurrency, etc)Knowledge on Distributed system architectureWorking knowledge of SDLC…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Associate Software Engineer - Java\n" +
            "Lowe's  3.5 3.5/5 rating - Charlotte, NC\n" +
            "$54,000 - $110,000 a year\n" +
            "Bachelor's Degree in Computer Science, CIS, or related field. Completion of coursework or program focused on software development or related skills (e.g.,…\n" +
            "Just posted\n" +
            "Junior Software Developer\n" +
            "Peraton  3.2 3.2/5 rating - United States\n" +
            "$66,000 - $106,000 a year\n" +
            "Use expertise to design, develop, code, test, and debug the solutions. Provide Analytical support and technical advice from initiation to implementation phases.\n" +
            "Just posted\n" +
            "Software Engineer\n" +
            "Braintrust  4.5 4.5/5 rating - San Francisco, CA\n" +
            "$100 - $110 an hour\n" +
            "As a software engineer on Seller Support Availability, you will be responsible for understanding incoming product documents and design specifications for any…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Software Engineer, Privacy\n" +
            "Reddit  4.2 4.2/5 rating - San Francisco, CA\n" +
            "$183,500 - $275,300 a year\n" +
            "Bachelor's Degree in computer science, information systems, or a similar STEM field or equivalent experience. Experience with data privacy requirements stemming…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Software Engineer, Backend\n" +
            "Brook Health - Seattle, WA\n" +
            "$130,000 - $160,000 a year\n" +
            "Peer review other team members' code, and learn and adapt from peer review of your own code. Brook Health is looking to hire a Senior Software Engineer, Backend…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Backend Java Software Engineer III\n" +
            "JPMorgan Chase Bank, N.A.  3.9 3.9/5 rating - Jersey City, NJ\n" +
            "$123,500 - $180,000 a year\n" +
            "Executes software solutions, design, development, and technical troubleshooting with ability to think beyond routine or conventional approaches to build…\n" +
            "Just posted\n" +
            "Software Engineer - Ubuntu Server Certification\n" +
            "Canonical - Jobs  3.5 3.5/5 rating - Los Angeles, CA\n" +
            "The Server Certification team develops Python based testing tools used to test Ubuntu Server on the latest enterprise hardware ensuring Ubuntu users have the…\n" +
            "Just posted\n" +
            "IoT Software Engineer\n" +
            "E Business international, Inc - Remote\n" +
            "While working in an Agile development process, you will design, implement, and document software to run on embedded and web platforms.\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Software Engineer - Ruby on Rails\n" +
            "Kongregate  3.5 3.5/5 rating - Remote\n" +
            "$95,000 - $115,000 a year\n" +
            "Software Engineers within the Kongregate Platform Engineering department will be a part of the technical team responsible for feature design/development,…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Senior Backend Engineer\n" +
            "ComplyAuto - Remote\n" +
            "$175,000 - $250,000 a year\n" +
            "Bachelor's degree in Computer Science, Engineering, or a related field; or equivalent work experience. Strong understanding of networking concepts, protocols,…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "Software Engineer - Backend (Dispatch/Tracking)\n" +
            "Motive  4 4/5 rating - San Francisco, CA\n" +
            "Mentor and learn from the developers within the engineering organization. 3+ years software product development experience building product, infrastructure,…\n" +
            "Easily apply\n" +
            "Just posted\n" +
            "View job feed\n" +
            " \n" +
            "\n" +
            "Overall, how relevant are these jobs?\n" +
            "Job relevance: 1- Not at all relevant\tJob relevance: 2- Slightly relevant\tJob relevance: 3- Somewhat relevant\tJob relevance: 4- Very relevant\tJob relevance: 5- Extremely relevant\n" +
            "\n" +
            "Not at all\n" +
            "\t\n" +
            "\n" +
            "Somewhat\n" +
            "\t\n" +
            "\n" +
            "Extremely\n" +
            "\n" +
            "Salaries estimated if unavailable. When a job posting doesn't include a salary, we estimate it by looking at similar jobs. Estimated salaries are not endorsed by the companies offering those positions and may vary from actual salaries.\n" +
            "\n" +
            "¹ This email includes Job Ads that are based on your Indeed Profile, preferences, and activity on Indeed. Indeed may be compensated by these employers, helping keep Indeed free for jobseekers. Indeed displays Job Ads based on a combination of employer bids and relevance, such as search terms, other information provided, and activities conducted on Indeed. For more information, see the Indeed Terms of Service.\n" +
            "\n" +
            "You are receiving daily Job Feed emails.\n" +
            "© 2023 Indeed, Inc.\n" +
            "6433 Champion Grandview Way, Building 1, Austin, TX 78750\n" +
            "Privacy Policy | Terms | Help Center\n" +
            "Manage email settings | Unsubscribe from this email\"";

    String jd1 = "I am  Deeksha, a recruiter with Pyramid Consulting, Inc. a leading staffing organization. I came across your profile in one of the job boards and feel that your skills and professional experience would be a good fit for a position with one of our premier clients. Please review the job description below. If you are interested in this position please forward your updated resume for immediate consideration and preferred time to discuss this opportunity further. \n" +
            "  \n" +
            "Title: Full Stack Java Developer  \n" +
            "Location: Charlotte, NC / Jersey City, NJ / Plano, TX  [Hybrid from day1  and 3 Days onsite]\n" +
            "Duration: +12 Months [Possible Extension]\n" +
            "PR Range: $58 - $65/hr. Employee benefits include, but are not limited to, health insurance (medical, dental, vision), 401(k) plan, and paid sick leave (depending on work location).\n" +
            "Note: H1B transfer will work here too.\n" +
            "         \n" +
            "Job Description:\n" +
            "Bachelors or Master’s Degree in Computer Science or related field.\n" +
            "7+ years of hands-on software development experience using Java / J2EE technologies.\n" +
            "Database.\n" +
            "UI (Angular/React JS)\n" +
            "Good interpersonal communication skills for technical and business conversations.\n" +
            "Good analytical skills to break down requirements and solve complex problems.\n" +
            "Proven task management and leadership skills.\n" +
            "Experience in server-side frameworks like Spring MVC.\n" +
            "Experience building restful web services.\n" +
            " \n" +
            "Desired Skills:\n" +
            "Experience in performance tuning with good understanding of JVM internals.\n" +
            "Experience with NoSQL databases like Cassandra.\n" +
            "Experience with open-source search engines like SOLR, elasticsearch.\n" +
            "Experience working with NLP and Machine learning.\n" +
            "Experience working in agile teams.\n" +
            "\n" +
            "Thanks and Regards,\n" +
            "Deeksha Shah\n" +
            "STEP - Recruiter\n" +
            "Pyramid Consulting, Inc\n" +
            "3060 Kimball Bridge Rd. Suite 200\n" +
            "Alpharetta, GA\n" +
            "Email: Deeksha.Shah@pyramidci.com; Desk: (415) 941-5840 Ext.5840\n" +
            "Web: www.pyramidci.com\n" +
            "USA | Canada | UK | India | Singapore";

    String jd2 = "Hi Anatolii,\n" +
            "\n" +
            "As we discussed, I am sending you this RTR and Salary confirmation for the position of Java Developer for 100% remote with Wipro Technologies.\n" +
            "\n" +
            " I ------------------------------------------------- confirm my Salary of $125k  + Wipro Benefits for the position of Java Developer (JD mentioned Below) for Wipro Technologies for 100% remote\n" +
            "\n" +
            " I also confirm that my work authorization status is   ---------------------------------------. I have also submitted all below required documents.\n" +
            "\n" +
            "·       Are you currently employed with government or government agency in any capacity or were employed in the last 12 months ?\n" +
            "\n" +
            "(The definition of Government employment includes employees of government companies, public sector undertakings, departments, institutions or any arm of any government, and foreign officials including officials of public international organizations)\n" +
            "\n" +
            "·         Resume:\n" +
            "\n" +
            "·         Alternate Email ID:\n" +
            "\n" +
            "·         LinkedIn ID: \n" +
            "\n" +
            "·         Full  Educational details with highest Education  with Passing year Bachler’s degree:\n" +
            "\n" +
            " \n" +
            "\n" +
            "Please reply with these details along with an updated copy of your resume:";

    @Test
    void searchSalaryTest() {

        Integer salary = salaryAnalyzer.searchSalary(jd2);
        System.out.println(salary);

    }


}
