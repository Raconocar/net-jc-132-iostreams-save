package training.netology;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static final String WORKDIR = "C:\\Users\\Stranger\\netology-prj-game";
    public static String SAVEDIR = WORKDIR + "\\Games\\savegames\\";

    private static void logger(String log) {
        try (FileWriter writer = new FileWriter(WORKDIR + "\\Games\\temp\\temp.txt", true)) {
            writer.write(log);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        GameProgress autosave0 = new GameProgress(100, 0, 1, 0);
        GameProgress autosave1 = new GameProgress(1, 0, 1, 10);
        GameProgress autosave2 = new GameProgress(30, 2, 2, 600);
        GameProgress autosave3 = new GameProgress(80, 10, 5, 9000);

        saveGame("save01.dat", autosave0);
        saveGame("save02.dat", autosave1);
        saveGame("save03.dat", autosave2);

        File savedir = new File(SAVEDIR);

        List<String> savefiles = new ArrayList<>();
        for (File file : savedir.listFiles()) {
            if (file.isFile()) {
                savefiles.add(file.getName());
            }
        }

        zipFiles(SAVEDIR + "SaveArch.zip", savefiles);

        File savedirtoclean = new File(SAVEDIR);

        List<String> saveFilesNames = new ArrayList<>();
        for (File file : savedirtoclean.listFiles()) {
            if (file.getName().endsWith(".dat")) {
                saveFilesNames.add(file.getName());
            }
        }
        saveFilesNames.forEach(e-> {
            try {
                Files.delete(Path.of(SAVEDIR+e));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        StringBuilder log = new StringBuilder();
        log.append("Очистка проведена успешно.\n");
        logger(log.toString());

    }

    private static void saveGame(String fileName, GameProgress gameProgress) {
        try (
                FileOutputStream fos = new FileOutputStream(SAVEDIR + fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder log = new StringBuilder();
        log.append("Сохранение прошло успешно. Файл сохранения - " + fileName + "\n");
        logger(log.toString());

    }

    private static void zipFiles(String zipPath, List<String> objects) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String s : objects
            ) {
                FileInputStream fis = new FileInputStream(SAVEDIR + s);
                ZipEntry inzip = new ZipEntry(s);
                zos.putNextEntry(inzip);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                logger("В архив добавлен файл " + s + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder log = new StringBuilder();
        log.append("Архивация прошла успешно. Файл сохранения - " + zipPath + "\n");
        logger(log.toString());

    }
}
