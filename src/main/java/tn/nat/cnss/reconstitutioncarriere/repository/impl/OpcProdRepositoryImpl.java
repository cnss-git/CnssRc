package tn.nat.cnss.reconstitutioncarriere.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tn.nat.cnss.reconstitutioncarriere.model.dto.RachatDto;
import tn.nat.cnss.reconstitutioncarriere.repository.OpcProdRepository;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;

@Repository("opcProdRepository")
public class OpcProdRepositoryImpl implements OpcProdRepository{
	
	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public void createRachatOrdrePaiement(RachatDto rachat, Structure structure, String matriculeAgent) {
		
		BigDecimal opcMvtSeq = (BigDecimal) em.createNativeQuery("select opc.OPC_MVT_SEQ.nextval@prod_prods from dual").getSingleResult();
		short nopCod = 0;
		if (rachat.getAssure().getRegCod()==320) nopCod = 2;
		else if (rachat.getAssure().getRegCod()==340) nopCod = 4;
		else if (rachat.getAssure().getRegCod()==350) nopCod = 5;
		
		
		em.createNativeQuery("insert into opc.mvt_opc@prod_prods " + 
				"( " + 
				"    bur_cod, bpy_nmvt, bpy_dtjour, " + 
				"    opr_cod, nop_cod, tyr_typ, " + 
				"    bpy_ide, bpy_cle, bpy_tide, " + 
				"    bpy_trim, bpy_annee, bpy_mois, " + 
				"    bpy_rd, bpy_momtant, bpy_dtregl, " + 
				"    bpy_ccaiss, bpy_mat, bpy_cann, " + 
				"    bpy_aval " + 
				") " + 
				"values " + 
				"( " + 
				"    :bur_cod, :bpy_nmvt, sysdate, " + 
				"    15, :nop_cod, 15, " + 
				"    :bpy_ide, :bpy_cle, 0, " + 
				"    :bpy_trim, :bpy_annee, :bpy_mois, " + 
				"    6, :bpy_momtant, trunc(sysdate), " + 
				"    :bpy_ccaiss, :bpy_mat, 0, " + 
				"    0 " + 
				")"
		)
		.setParameter("bur_cod", structure.getBurCod())
		.setParameter("bpy_nmvt", opcMvtSeq)
		.setParameter("nop_cod", nopCod)
		.setParameter("bpy_ide", rachat.getAssure().getAssMat())
		.setParameter("bpy_cle", rachat.getAssure().getAssCle())
		.setParameter("bpy_trim", LocalDate.now().get(IsoFields.QUARTER_OF_YEAR))
		.setParameter("bpy_annee", LocalDate.now().getYear())
		.setParameter("bpy_mois", LocalDate.now().getMonthValue())
		.setParameter("bpy_momtant", (float)rachat.getTotalCotisationRachat()/1000)
		.setParameter("bpy_ccaiss", structure.getBurCod())
		.setParameter("bpy_mat", matriculeAgent)
		.executeUpdate();
		
	}
	
	

}
