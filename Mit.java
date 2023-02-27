import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Mit {
    private final InputView inputView = new InputView();

    public void run() throws IOException, NoSuchAlgorithmException {
        boolean running = true;
        while (running) {
            String input = inputView.getInput();
            String[] inputs = input.split(" ");
            String command = inputs[1];
            String path = "";
            if (inputs.length == 3) {
                path = inputs[2];
            }
            switch (command) {
                case "list":
                    list(path);
                    break;
                case "hash":
                    hash(path);
                    break;
                case "zlib":
                    zlib(path);
                    break;
                case "exit":
                    inputView.stop();
                    running = false;
                    break;
            }
        }
    }

    private void list(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files == null) {
            System.out.println("해당 경로에 파일이 존재하지 않습니다.");
            return;
        }

        for (File file : files) {
            String filename = file.getName();
            long fileSize = file.length() / 1024; // kilobyte
            System.out.println(filename + " " + fileSize + "KB");
        }
        System.out.println();
    }

    private void hash(String path) throws NoSuchAlgorithmException, IOException {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files == null) {
            try {
                throw new FileNotFoundException("해당 경로에 파일이 존재하지 않습니다.");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (File file : files) {
            digest.update(Files.readAllBytes(file.toPath()));
            System.out.println(file.getName() + " : " + byteToHex(digest.digest()));
        }
        System.out.println();
    }

    private String byteToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(Integer.toHexString(0xff & b));
        }
        return builder.toString();
    }

    private void zlib(String path) {

    }
}