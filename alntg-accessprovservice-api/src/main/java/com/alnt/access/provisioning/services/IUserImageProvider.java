package com.alnt.access.provisioning.services;

public interface IUserImageProvider {
	public byte[] downloadImageForUserId(String childUserId);
}
