package ru.ilka.leetcode.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LeetcodeService {
    private static final Logger logger = LogManager.getLogger(LeetcodeService.class);

    private static final String DIFFICULTY_CLASS_EASY = "pull-right difficulty-label round label label-Easy";
    private static final String DIFFICULTY_CLASS_MEDIUM = "pull-right difficulty-label round label label-Medium";
    private static final String DIFFICULTY_CLASS_HARD = "pull-right difficulty-label round label label-Hard";

    public String findDifficulty(String problemURL) {
        Document document = null;
        try {
            document = Jsoup.connect(problemURL).get();
            System.out.println(document.toString());
        } catch (IOException e) {
            logger.error("Can not load document from " + problemURL);
        }
        Elements difficultyLabel = null;
        try {
            difficultyLabel = document.getElementsByClass(DIFFICULTY_CLASS_EASY);
        } catch (NullPointerException e) {
            logger.error("Can not get difficulty by " + DIFFICULTY_CLASS_EASY, e);
            try {
                difficultyLabel = document.getElementsByClass(DIFFICULTY_CLASS_MEDIUM);
            } catch (NullPointerException ex) {
                logger.error("Can not get difficulty by " + DIFFICULTY_CLASS_MEDIUM, ex);
                try {
                    difficultyLabel = document.getElementsByClass(DIFFICULTY_CLASS_HARD);
                } catch (NullPointerException e3) {
                    logger.error("Can not get difficulty by " + DIFFICULTY_CLASS_HARD, e3);
                }
            }
        }
        logger.debug(difficultyLabel);
        return difficultyLabel.toString();
    }
}
