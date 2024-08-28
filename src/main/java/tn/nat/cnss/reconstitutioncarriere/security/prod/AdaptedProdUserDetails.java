package tn.nat.cnss.reconstitutioncarriere.security.prod;

import local.cnss.security.client.CnssUserDetails;
import local.cnss.security.dto.ConnectionDetailsDTO;

public class AdaptedProdUserDetails extends CnssUserDetails{
	
	private static final long serialVersionUID = 7354593783310345231L;
	
	
	private Structure connectedFrom;
	private Structure belongsTo;
	

	
	public AdaptedProdUserDetails(ConnectionDetailsDTO connectionDetails, String token, Integer applicationId) {
		super(connectionDetails, token, applicationId);
	}
	
	

	public Structure getConnectedFrom() {
		return connectedFrom;
	}
	public void setConnectedFrom(Structure connectedFrom) {
		this.connectedFrom = connectedFrom;
	}



	public Structure getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(Structure belongsTo) {
		this.belongsTo = belongsTo;
	}

	

}
