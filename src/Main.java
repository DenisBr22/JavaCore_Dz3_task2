import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void main(String[] args) {

        String rootPatch = "D://Games/savegames/";
        List<String> patches = new ArrayList<>();

        GameProgress stage1 = new GameProgress(10, 4, 15, 100.0);
        GameProgress stage2 = new GameProgress(14, 5, 11, 200.0);
        GameProgress stage3 = new GameProgress(20, 5, 20, 300.0);

        String patch1 = rootPatch + "save_1.data";
        patches.add(patch1);
        String patch2 = rootPatch + "save_2.data";
        patches.add(patch2);
        String patch3 = rootPatch + "save_3.data";
        patches.add(patch3);

        saveGame(patch1, stage1);
        saveGame(patch2, stage2);
        saveGame(patch3, stage3);

        String zipPatch = rootPatch + "zip_save.zip";

        zipFiles(zipPatch, patches);

    }

    public static void saveGame(String patch, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(patch);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String patchZip, List<String> list) {
        try (FileOutputStream fos = new FileOutputStream(patchZip);
             ZipOutputStream zout = new ZipOutputStream(fos)) {

            for (String sPatch : list) {
                File archivFile = new File(sPatch);

                try (FileInputStream fis = new FileInputStream(sPatch)) {
                    zout.putNextEntry(new ZipEntry(archivFile.getName()));
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                archivFile.delete();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
