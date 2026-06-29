package utils;

public class FileUtils {

    public static String getCategory(String fileName) {

    fileName = fileName.toLowerCase();

    // 🔹 Special categories (keep these first)
    if (fileName.contains("resume") || fileName.contains("cv"))
        return "Resume";

    if (fileName.contains("invoice") || fileName.contains("bill"))
        return "Bills";

    // 🔹 Documents
    if (fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx")
            || fileName.endsWith(".txt") || fileName.endsWith(".ppt") || fileName.endsWith(".pptx")
            || fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))
        return "Documents";

    // 🔹 Images
    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
            || fileName.endsWith(".gif") || fileName.endsWith(".bmp") || fileName.endsWith(".svg")
            || fileName.endsWith(".ico"))
        return "Images";

    // 🔹 Videos
    if (fileName.endsWith(".mp4") || fileName.endsWith(".mkv") || fileName.endsWith(".avi")
            || fileName.endsWith(".mov") || fileName.endsWith(".wmv"))
        return "Videos";

    // 🔹 Code
    if (fileName.endsWith(".py") || fileName.endsWith(".java") || fileName.endsWith(".js")
            || fileName.endsWith(".html") || fileName.endsWith(".css")
            || fileName.endsWith(".c") || fileName.endsWith(".cpp"))
        return "Code";

    // 🔹 Archives (NEW)
    if (fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".tar"))
        return "Archives";

    return "Others";
}
    public static String cleanFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9\\.]", "_");
    }
}