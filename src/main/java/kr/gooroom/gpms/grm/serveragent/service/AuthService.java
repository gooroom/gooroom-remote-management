package kr.gooroom.gpms.grm.serveragent.service;

import java.util.List;

public interface AuthService {

	public List<AuthVO> selectTrmut(String trmutId) throws Exception;

}
