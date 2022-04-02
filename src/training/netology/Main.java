package training.netology;

import java.io.*;

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

    public static void main(String[] args) {
        GameProgress autosave0 = new GameProgress(100, 0, 1, 0);
        GameProgress autosave1 = new GameProgress(1, 0, 1, 10);
        GameProgress autosave2 = new GameProgress(30, 2, 2, 600);
        GameProgress autosave3 = new GameProgress(80, 10, 5, 9000);
        saveGame("save01.dat",autosave0);
        saveGame("save02.dat",autosave1);
        saveGame("save03.dat",autosave2);
    }

    private static void saveGame(String fileName, GameProgress gameProgress) {
        try {
            FileOutputStream fos = new FileOutputStream(SAVEDIR + fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
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
}
