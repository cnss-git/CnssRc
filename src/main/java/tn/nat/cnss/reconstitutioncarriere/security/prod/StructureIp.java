package tn.nat.cnss.reconstitutioncarriere.security.prod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "V_AUTHWEB_STRUCTURE_IP")
public class StructureIp implements Serializable{

	private static final long serialVersionUID = 5937159075067504588L;
	
	
	@Id
	@Column(name = "STRIP_IP_PLAGE")
	private String stripIpPlage;
	
	@ManyToOne
	@JoinColumn(name = "STRIP_STRUCTURE", referencedColumnName = "BUR_COD")
	private Structure structure;

	public String getStripIpPlage() {
		return stripIpPlage;
	}
	public void setStripIpPlage(String stripIpPlage) {
		this.stripIpPlage = stripIpPlage;
	}

	public Structure getStructure() {
		return structure;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	

}
