package mtf.project.service;

import mtf.project.model.FileModel;
import mtf.project.repository.FileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDb fileDb;

    @Override
    public FileModel store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileModel fileModel = new FileModel();
        fileModel.setData(file.getBytes());
        fileModel.setName(fileName);
        fileModel.setType(file.getContentType());
        return fileDb.save(fileModel);
    }

    @Override
    public FileModel getFile(String id) {
        return fileDb.findById(id).get();
    }

    @Override
    public void deleteFile(FileModel file) {
        fileDb.delete(file);
    }

    @Override
    public Stream<FileModel> getAllFiles() {
        return fileDb.findAll().stream();
    }
}
