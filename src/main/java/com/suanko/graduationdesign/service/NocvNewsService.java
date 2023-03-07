package com.suanko.graduationdesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suanko.graduationdesign.entity.NocvNews;

import java.util.List;

public interface NocvNewsService extends IService<NocvNews> {
    List<NocvNews> listNewsLimit5();
}
