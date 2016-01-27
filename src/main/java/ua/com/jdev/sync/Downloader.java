package ua.com.jdev.sync;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.io.InputStream;


public class Downloader {

    /**
     * Download a file's content.
     *
     * @param service Drive API service instance.
     * @param file    Drive File instance.
     * @return InputStream containing the file's content if successful,
     * {@code null} otherwise.
     */
    private static InputStream downloadFile(Drive service, File file) {
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            try {
                // uses alt=media query parameter to request content
                return service.files().get(file.getId()).executeMediaAsInputStream();
            } catch (IOException e) {
                // An error occurred.
                e.printStackTrace();
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }
}