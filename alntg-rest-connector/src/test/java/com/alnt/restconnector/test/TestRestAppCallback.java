package com.alnt.restconnector.test;

import java.util.Collection;
import java.util.List;

import com.alnt.access.certification.model.IUserCertificationInformation;
import com.alnt.extractionconnector.common.model.IRoleInformation;
import com.alnt.extractionconnector.common.model.IUserInformation;
import com.alnt.extractionconnector.common.model.ReconExtensionInfo;
import com.alnt.extractionconnector.common.service.ISearchCallback;

public class TestRestAppCallback 
				implements ISearchCallback {

	private int resultSet = 1;
	public void processSearchResult(List details) throws Exception {
		
		for(int i=0;i<details.size();i++){
			Object object = details.get(i);
			if(object instanceof IRoleInformation){
				IRoleInformation roleInformation = (IRoleInformation)details.get(i);
				System.out.println("Role name "+roleInformation.getName());	
				System.out.println("Member data "+roleInformation.getMemberData());
			}else
			if(object instanceof IUserInformation){
				IUserInformation userInformation = (IUserInformation)details.get(i);
				//System.out.println("Users and entitlements = "+userInformation.getUserDetails()+ " Entittlements" + userInformation.getEntitlements());
				System.out.println("User Id == " + userInformation.getUserDetails().get("UserId"));
				System.out.println("Users and entitlements = "+userInformation.getUserDetails()+ " " + userInformation.getEntitlements());
				
				/*for (Object object2 : values) {
					if(object2 instanceof MultiValueDetails){
						List<MultiValue> accounsList = ((MultiValueDetails)object2).getMultiValues();	
						Set<String> multiVals = new HashSet<String>();
						for (MultiValue multiValue : accounsList) {
							multiVals.add(multiValue.getValue());
							System.out.println("AccountId = "+multiValue.getValue() + "    Action = "+multiValue.getAction());
//							System.out.println();
						}	
						
					}
					
				}*/
				
			}else
				if(object instanceof IUserCertificationInformation){
					IUserCertificationInformation userCertInformation = (IUserCertificationInformation)details.get(i);
					//System.out.println("Users and entitlements = "+userInformation.getUserDetails()+ " Entittlements" + userInformation.getEntitlements());
					System.out.println("User Id == " + userCertInformation.getUserId());
					System.out.println("User Certification Details == " +userCertInformation.getCertificationInformation());
				}
		}
		System.out.println("************************** Results processed for set "+resultSet+ "*******************");
		resultSet++;
	}
	
	public void processSearchResult(List arg0, ReconExtensionInfo arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
