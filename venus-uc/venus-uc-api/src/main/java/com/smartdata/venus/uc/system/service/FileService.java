package com.smartdata.venus.uc.system.service;

import com.smartdata.venus.uc.domain.File;

/**
 * @author khlu
 * @date 2018/11/02
 */
public interface FileService {
    File isFile(String sha1);

    File save(File file);
}

