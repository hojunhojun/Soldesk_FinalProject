package com.ung.recipetoyou.member;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ung.recipetoyou.LeeFileNameGenerator;
import com.ung.recipetoyou.community.CommunityPostRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberDAO {

	@Value("${rty.member.image}")
	private String memberFolder;

	@Value("${member.photo.size}")
	private int photoSize;
	
	@Value("${rty.badge.image}")
	private String badgeFolder;
	
	@Autowired
	private MemberRepository mr;

	@Autowired
	private CommunityPostRepository cpr;
	
	private BCryptPasswordEncoder bcpe; // 복호화 안되는거
	
	private SimpleDateFormat sdf;

	public MemberDAO() {
		bcpe = new BCryptPasswordEncoder();
		sdf = new SimpleDateFormat("yyyyMMdd");
	}

	public boolean isLogined(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m != null) {
			req.setAttribute("loginPage", "member/logined");
			return true;
		}
		req.setAttribute("loginPage", "member/login");
		return false;
	}

	public Resource getBadgeImage(String n) {
		try {
			return new UrlResource("file:" + badgeFolder + "/" + n);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Resource getImage(String n) {
		try {
			return new UrlResource("file:" + memberFolder + "/" + n);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void join(MultipartFile mf, Member m, HttpServletRequest req) {
		String fileName = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception();
			}
			fileName = LeeFileNameGenerator.generateFileName(mf);
			mf.transferTo(new File(memberFolder + "/" + fileName));
			m.setPphoto(fileName);
			
			m.setPw(bcpe.encode(m.getPw()));

			String bd = String.format("%s%02d%02d", req.getParameter("y"),
					Integer.parseInt(req.getParameter("m")), Integer.parseInt(req.getParameter("d")));
			
			Date birthday = sdf.parse(bd);
			m.setBir(birthday);

			String address = req.getParameter("addr2") + "!" + req.getParameter("addr3") + "!"
					+ req.getParameter("addr1");
			m.setAddr(address);


			if (mr.existsById(m.getId())) {
				throw new Exception();
			}
			m.setBg("1.png");
			m.setType("1");
			mr.save(m);
		} catch (Exception e) {
			new File(memberFolder + "/" + fileName).delete();
			e.printStackTrace();
		}
	}
	
	public void login(Member m, HttpServletRequest req) {
		try {
			Optional<Member> memberTemp = mr.findById(m.getId());
			if (memberTemp.isPresent()) {
				Member dbMember = memberTemp.get();
				if (bcpe.matches(m.getPw(), dbMember.getPw())) {
					req.getSession().setAttribute("loginMember", dbMember);
					req.getSession().setMaxInactiveInterval(10000);
				} else {
					
				}
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkBadge(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
	    long badgeCount = cpr.countByWriter(m);
	    Optional<Member> memberTemp = mr.findById(m.getId());
	    Member dbMember = memberTemp.get();
	    
	    if (badgeCount > 40) {
            dbMember.setBg("5.png");
        } else if (badgeCount > 30) {
            dbMember.setBg("4.png");
        } else if (badgeCount > 20) {
            dbMember.setBg("3.png");
        } else if (badgeCount > 10) {
            dbMember.setBg("2.png");
        } else {
        	dbMember.setBg("1.png");
        }
	    
	    if (m.getBg() != dbMember.getBg()) {
	    	mr.save(dbMember);	    	
	    }
	    req.getSession().setAttribute("loginMember", dbMember);
	}
	
	public void logout(HttpServletRequest req) {
		req.getSession().setAttribute("loginMember", null);
	}
	
	public void bye(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			mr.delete(m);
			new File(memberFolder + "/" + m.getPphoto()).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(MultipartFile mf, Member m, HttpServletRequest req) {
		Member loginMember = (Member) req.getSession().getAttribute("loginMember");
		String oldFile = loginMember.getPphoto();
		String newFile = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception();
			}
			newFile = LeeFileNameGenerator.generateFileName(mf);
			mf.transferTo(new File(memberFolder + "/" + newFile));
			if (newFile == null) {
				newFile = oldFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {
			loginMember.setPw(bcpe.encode(m.getPw()));
			loginMember.setName(loginMember.getName());
			loginMember.setNick(m.getNick());
			String address = req.getParameter("addr2") + "!" + req.getParameter("addr3") + "!"
					+ req.getParameter("addr1");
			loginMember.setAddr(address);
			loginMember.setPphoto(newFile);
			mr.save(loginMember);
			
			req.getSession().setAttribute("loginMember", loginMember);
			if (!newFile.equals(oldFile)) {
				new File(memberFolder + "/" + oldFile).delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!newFile.equals(oldFile)) {
				new File(memberFolder + "/" + newFile).delete();
			}
		}
	}

	public void splitAddress(HttpServletRequest request) {
		Member m = (Member) request.getSession().getAttribute("loginMember");
		String addr = m.getAddr();
		String[] addrs = addr.split("!");
		request.setAttribute("addr1", addrs[2]);
		request.setAttribute("addr2", addrs[0]);
		request.setAttribute("addr3", addrs[1]);
	}

	public boolean infoCheck(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (bcpe.matches(req.getParameter("infoCheck"), m.getPw())) {
			req.setAttribute("contentPage", "member/info");
			return true;
		} else {
			req.setAttribute("contentPage", "home");
			return false;
		}
	}

	public Members getMemberIdToJSON(Member m) {
		Optional<Member> memberTemp = mr.findById(m.getId());
		List<Member> member = new ArrayList<>();
		if (memberTemp.isPresent()) {
			member.add(memberTemp.get());
		}
		return new Members(member);
	}
	
	public Members getMemberNickToJSON(Member m) {
		Optional<Member> memberTemp = mr.findByNick(m.getNick());
		List<Member> member = new ArrayList<>();
		if (memberTemp.isPresent()) {
			member.add(memberTemp.get());
		}
		return new Members(member);
	}
	
}