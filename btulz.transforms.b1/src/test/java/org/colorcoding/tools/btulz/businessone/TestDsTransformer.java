package org.colorcoding.tools.btulz.businessone;

import java.io.File;
import java.util.ArrayList;

import org.colorcoding.tools.btulz.Environment;
import org.colorcoding.tools.btulz.businessone.command.Command4Ds;
import org.colorcoding.tools.btulz.businessone.transformer.DsTransformer;

import com.sap.smb.sbo.api.ICompany;
import com.sap.smb.sbo.api.IField;
import com.sap.smb.sbo.api.IRecordset;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.SBOCOMUtil;

import junit.framework.TestCase;

public class TestDsTransformer extends TestCase {

	public void testB1() {
		ICompany company = SBOCOMUtil.newCompany();
		company.setDbServerType(SBOCOMConstants.BoDataServerTypes_dst_MSSQL2014);
		company.setServer("ibas-demo-b1");
		company.setCompanyDB("SBODemoCN");
		company.setUserName("manager");
		company.setPassword("manager");
		company.setDbUserName("sa");
		company.setDbPassword("1q2w3e");
		company.setLicenseServer("ibas-demo-b1:30000");
		company.setSLDServer("ibas-demo-b1:40000");
		company.setLanguage(SBOCOMConstants.BoSuppLangs_ln_English);
		IRecordset recordset = company.getCompanyList();
		while (!recordset.isEoF()) {
			for (int i = 0; i < recordset.getFields().getCount(); i++) {
				IField field = recordset.getFields().item(i);
				System.out.print(field.getValue());
				System.out.print(" ");
			}
			System.out.println();
			recordset.moveNext();
		}
		if (company.connect() != 0) {
			System.err.println(String.format("company is not connected, [%s %s]", company.getLastErrorCode(),
					company.getLastErrorDescription()));
		} else {
			System.out.println(String.format("company [%s] is connected.", company.getCompanyDB()));
		}
	}

	public void testDs() throws Exception {
		DsTransformer dsTransformer = new DsTransformer();
		dsTransformer
				.addDomains(Environment.getWorkingFolder() + "/ds_tt_userobjects.xml".replace("/", File.separator));
		dsTransformer.setServer("ibas-demo-b1");
		dsTransformer.setCompanyDB("SBODemoCN");
		dsTransformer.setUserName("manager");
		dsTransformer.setPassword("manager");
		dsTransformer.setDbServerType(SBOCOMConstants.BoDataServerTypes_dst_MSSQL2014);
		dsTransformer.setDbUserName("sa");
		dsTransformer.setDbPassword("1q2w3e");
		dsTransformer.transform();
	}

	public void testCmd() {
		ArrayList<String> args = new ArrayList<>();
		args.add(String.format(Command4Ds.COMMAND_PROMPT));
		args.add(String.format("-Server=%s", "ibas-demo-b1"));
		args.add(String.format("-CompanyDB=%s", "SBODemoCN"));
		args.add(String.format("-UserName=%s", "manager"));
		args.add(String.format("-Password=%s", "manager"));
		args.add(String.format("-DbServerType=%s", "8"));
		args.add(String.format("-DbUser=%s", "sa"));
		args.add(String.format("-DbPassword=%s", "1q2w3e"));
		args.add(String.format("-LicenseServer=%s", ""));
		args.add(String.format("-SLDServer=%s", ""));
		args.add(String.format("-Language=%s", "15"));
		System.out.println("显示帮助信息：");
		Console.main(new String[] { Command4Ds.COMMAND_PROMPT, Command4Ds.ARGUMENT_NAME_HELP });
		System.out.println("开始运行：");
		Console.main(args.toArray(new String[] {}));
	}
}
