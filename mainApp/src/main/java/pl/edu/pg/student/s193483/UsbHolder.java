package pl.edu.pg.student.s193483;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsbHolder {
    private final JLabel usbStatus;
    private final JLabel status;
    File[] roots = new File[0];
    public File usb = null;
    public byte[] privateKeyBytes;

    public UsbHolder(JLabel usbStatus, JLabel status) {
        this.usbStatus = usbStatus;
        this.status = status;
    }


    public void updateRoots(File[] newRoots) {
        List<File> detected = new ArrayList<File>();

        if (newRoots.length > roots.length && roots.length != 0) {
            new_root_loop:
            for (File newRoot : newRoots) {
                for (File oldRoot : roots) {
                    if (newRoot.equals(oldRoot)) continue new_root_loop;
                }
                detected.add(newRoot);
            }

            if (detected.size() > 1) {
                usbStatus.setText("Multiple new drives detected, insert one at a time");
            }
            else if (detected.size() == 1) {
                usb = detected.getFirst();
                usbStatus.setText("Detected USB drive " + usb.toString());

                loadPrivateKeyBytes();
            }
        }

        usb_removed_check:
        if (newRoots.length < roots.length) {
            for (File newRoot : newRoots) {
                if (newRoot.equals(usb)) break usb_removed_check;
            }

            usb = null;
            usbStatus.setText("USB drive removed");
        }

        roots = newRoots;
    }

    public void loadPrivateKeyBytes() {
        File[] keyFiles = usb.listFiles((_, name) -> name.endsWith(".key"));

        if (keyFiles != null) {
            if (keyFiles.length == 0) {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - no keys detected");
            }
            else if (keyFiles.length > 1) {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - multiple keys detected");
            }
            else {
                usbStatus.setText("Detected USB drive " + usb.toString() + " - detected key file " + keyFiles[0].getName());
                try (FileInputStream fileInputStream = new FileInputStream(keyFiles[0].getAbsolutePath())) {
                    privateKeyBytes = fileInputStream.readAllBytes();
                } catch (IOException e) {
                    status.setText("Could not read private key file");
                }
            }
        }
    }
}
