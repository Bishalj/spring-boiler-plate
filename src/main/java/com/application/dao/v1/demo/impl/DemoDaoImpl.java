package com.application.dao.v1.demo.impl;

import com.application.dao.v1.common.AbstractBaseDaoImpl;
import com.application.dao.v1.demo.IDemoDao;
import com.application.models.v1.Demo;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository("DemoDaoImpl")
public class DemoDaoImpl extends AbstractBaseDaoImpl<Demo>  implements IDemoDao {
}
