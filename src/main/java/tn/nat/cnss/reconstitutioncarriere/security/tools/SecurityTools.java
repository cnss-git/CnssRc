package tn.nat.cnss.reconstitutioncarriere.security.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;
import tn.nat.cnss.reconstitutioncarriere.security.prod.StructureIpRepository;
import tn.nat.cnss.reconstitutioncarriere.security.prod.StructureRepository;




@Component
public class SecurityTools {
	
	
	@Autowired
	private StructureRepository structureRepository;
	
	@Autowired
	private StructureIpRepository structureIpRepository;
	
	
	
	
	
	public Structure decodeConnectedFrom(HttpServletRequest request) throws UnknownHostException { 
		
		//System.out.println(request.getRemoteAddr());
		
		String clientIpAddress = request.getHeader("X-FORWARDED-FOR");
		if (clientIpAddress == null) {
		    clientIpAddress = request.getRemoteAddr();
		}
		String serverIpAddress = InetAddress.getLocalHost().getHostAddress();
		//System.out.println("client IP:" + clientIpAddress + " server IP:  "+serverIpAddress);
		
		//127.0.0.1, 0:0:0:0:0:0:0:1
		if(clientIpAddress.compareTo("127.0.0.1")==0 || clientIpAddress.compareTo("0:0:0:0:0:0:0:1")==0 || clientIpAddress.compareTo(serverIpAddress)==0) {
			return structureRepository.findById((short)88).orElse(null);
		}else {
			String clientIplPlage = clientIpAddress.split("\\.")[0]+"."+clientIpAddress.split("\\.")[1]+"."+clientIpAddress.split("\\.")[2]+".";
			//System.out.println(clientIplPlage);
			// VPN
			if(clientIplPlage.compareTo("10.40.100.") == 0) {
				Structure vpn = new Structure();
				vpn.setBurCod((short)-1);
				vpn.setBurIntit("VPN");
				vpn.setBurIntitAr("VPN");
				return vpn;
			}else { // Réseau local
				return structureIpRepository.findById(clientIplPlage).orElse(null).getStructure();
			}
		}
		
		
	}

}
