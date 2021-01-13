package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.gooroom.gpms.grm.serveragent.service.AuthService;
import kr.gooroom.gpms.grm.serveragent.service.AuthVO;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource(name = "authDAO")
    private AuthDAO authDAO;
    
	@Override
	public List<AuthVO> selectTrmut(String trmutId) throws Exception {
		return authDAO.selectTrmut(trmutId);
	}

}
