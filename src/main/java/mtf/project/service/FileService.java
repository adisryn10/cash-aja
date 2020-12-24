package mtf.project.service;

import mtf.project.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileService {
    FileModel store(MultipartFile file) throws IOException;

    FileModel getFile(String id);

    void deleteFile(FileModel file);

    Stream<FileModel> getAllFiles();
}
