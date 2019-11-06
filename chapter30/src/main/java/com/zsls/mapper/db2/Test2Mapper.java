package com.zsls.mapper.db2;

import com.zsls.model.Test2;

import java.util.List;
import java.util.Map;

public interface Test2Mapper {

	void getsave(Test2 t);

	List<Map<String, Object>> getInfo();

}
