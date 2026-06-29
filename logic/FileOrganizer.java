package logic;

import java.io.File;
import java.nio.file.*;
import java.util.*;
import utils.FileUtils;

public class FileOrganizer {

    public static Map<String, String> organize(File folder, List<String> output) {

        Map<String, String> moveHistory = new LinkedHashMap<>();

        File[] files = folder.listFiles();
        if (files == null) return moveHistory;

        for (File file : files) {

            if (file.isFile()) {

                String name = file.getName();
                String category = FileUtils.getCategory(name);
                String newName = FileUtils.cleanFileName(name);

                File categoryFolder = new File(folder, category);
                if (!categoryFolder.exists()) {
                    categoryFolder.mkdirs(); // FIXED
                }

                try {
                    Path source = file.toPath();
                    Path destination = new File(categoryFolder, newName).toPath();

                    Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);

                    moveHistory.put(source.toString(), destination.toString());

                    output.add(name + " → " + category);

                } catch (Exception e) {
                    output.add(name + " → error");
                }

            } else if (file.isDirectory()) {
                moveHistory.putAll(organize(file, output));
            }
        }

        return moveHistory;
    }

    public static void undo(Map<String, String> moveHistory) {

        for (Map.Entry<String, String> entry : moveHistory.entrySet()) {
            try {
                Files.move(
                        Paths.get(entry.getValue()),
                        Paths.get(entry.getKey()),
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (Exception e) {
                System.out.println("Undo failed: " + entry.getValue());
            }
        }
    }
}