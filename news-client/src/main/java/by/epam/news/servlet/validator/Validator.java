package by.epam.news.servlet.validator;

public class Validator {
    public static boolean validateComment(String comment) {
        return comment.length() <= 100;
    }
}
