package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.Asset;



public interface AssetRepo extends JpaRepository<Asset, Long>{

	@Query("from Asset a where a.siteId=:siteId and a.assetCode=:assetCode and a.delFlag=0")
	public List<Asset> findBySiteIdAndCode(@Param(value="siteId") Long siteId, @Param(value="assetCode") String assetCode);

	@Query("from Asset a where a.siteId=:siteId  and a.delFlag=0")
	public List<Asset> findBySiteId(@Param(value="siteId") Long siteId);

	@Query("from Asset a where a.categoryId=:categoryId  and a.delFlag=0")
	public List<Asset> findByCategoryId(@Param(value="categoryId") Long categoryId);
	
	@Query("from Asset a where a.siteId in :siteId  and a.delFlag=0 order by a.assetName")
	public List<Asset> findBySiteIdIn(@Param("siteId") List<Long> siteId);
	
	public List<Asset> findByAssetCodeAndSiteIdInAndDelFlag(String accetCode, List<Long> siteId, int delFlag);
	

}
