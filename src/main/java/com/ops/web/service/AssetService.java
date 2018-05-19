package com.ops.web.service;

import java.util.List;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.AssetVO;
import com.ops.app.vo.LoginUser;
import com.ops.jpa.entities.AssetCategory;
import com.ops.jpa.entities.AssetLocation;


public interface AssetService {

	public List<AssetVO> findAllAsset(LoginUser user) throws Exception;

	public List<AssetVO> findAssetsBySite(Long siteId) throws Exception;

	public AssetVO findAssetById(Long assetid);

	public AssetVO findAssetByModelNumber(String modelNumber) throws Exception; 

	public RestResponse saveOrUpdateAsset(AssetVO assetVO, LoginUser loginUser) throws Exception;

	public List<AssetCategory> getAllAssetCategories() throws Exception;

	public List<AssetLocation> getAllAssetLocations() throws Exception;

	public AssetVO deleteAsset(Long assetId) throws Exception;

}
