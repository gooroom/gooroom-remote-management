package kr.gooroom.gpms.grm.serveragent.service;

import java.util.List;

public interface AuthService {

	List<AuthVO> selectTrmut(String trmutId) throws Exception;

}
