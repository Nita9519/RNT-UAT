package ai.rnt.ams.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import ai.rnt.ams.model.Admin;
import ai.rnt.ams.model.AssetRegistration;
import ai.rnt.main.dao.DBConnect;

public class AssetRegistrationDao {
	private static final Logger log = (Logger) LogManager.getLogger(AssetRegistrationDao.class);
	AdminDao adminDao = new AdminDao();
	DBConnect dbConnect = new DBConnect();
	ResultSet rs1 = null;

	public ArrayList<AssetRegistration> getAvailableAssetList() throws SQLException, PropertyVetoException {

		AssetRegistration assetRegistration = new AssetRegistration();
		ArrayList<AssetRegistration> availableAssetList = new ArrayList<AssetRegistration>();
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT ar.Asset_Id,");
		queryString.append(" ar.Asset_Tag,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name,");
		queryString.append(" sup.Supplier,");
		queryString.append(" ar.Product,");
		queryString.append(" ar.Vesrion_Model,");
		queryString.append(" ar.Serial_No,");
		queryString.append(" ar.Cost,");
		queryString.append(" ar.Product_Key,");
		queryString.append(" ar.Processor,");
		queryString.append(" ar.Ram,");
		queryString.append(" ar.Hard_Disk,");
		queryString.append(" ar.Warranty,");
		queryString.append(" ar.Warranty_Coverage,");
		queryString.append(" ar.Accidential_Damage,");
		queryString.append(" ar.Asset_Owner,");
		queryString.append(" lm.Location,");
		queryString.append(" ar.Asset_Available_Status");
		queryString.append(" FROM Asset_Category_Master AS ac ");
		queryString.append(" INNER JOIN Asset_Registration AS ar ");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc ");
		queryString.append(" INNER JOIN Asset_Supplier_Master AS sup ");
		queryString.append(" INNER JOIN location_master AS lm ");
		queryString.append(" WHERE ac.Asset_Cat_Id = ar.Asset_Cat_Id ");
		queryString.append(" AND sc.Sub_Cat_Id = ar.Sub_Cat_Id ");
		queryString.append(" AND lm.Location_Id = ar.Location_Id ");
		queryString.append(" AND sup.Supplier_Id = ar.Supplier_Id ");
		queryString.append(" AND ar.Asset_Available_Status = 'Available' ");
		 queryString.append(" AND ar.Deleted_By is NULL"); 
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			rs1 = stmt.executeQuery(queryString.toString());

			while (rs1.next()) {
				assetRegistration = new AssetRegistration();
				assetRegistration.setAssetId(rs1.getInt(1));
				assetRegistration.setAssetTag(rs1.getString(2));
				assetRegistration.setAssetCatName(rs1.getString(3));
				assetRegistration.setSubCatName(rs1.getString(4));
				assetRegistration.setSupplier(rs1.getString(5));
				assetRegistration.setProduct(rs1.getString(6));
				assetRegistration.setVersionModel(rs1.getString(7));
				assetRegistration.setSerialNo(rs1.getString(8));
				assetRegistration.setCost(rs1.getInt(9));
				assetRegistration.setProductKey(rs1.getString(10));
				assetRegistration.setProcessor(rs1.getString(11));
				assetRegistration.setRam(rs1.getString(12));
				assetRegistration.setHardDisk(rs1.getString(13));
				assetRegistration.setWarranty(rs1.getString(14));
				assetRegistration.setWarrantyCoverage(rs1.getString(15));
				assetRegistration.setAccidentialDamage(rs1.getString(16));
				assetRegistration.setAssetOwner(rs1.getString(17));
				assetRegistration.setLocation(rs1.getString(18));
				assetRegistration.setAssetAvailableStatus(rs1.getString(19));
				availableAssetList.add(assetRegistration);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs1.close();
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
		}

		return availableAssetList;
	}

	public ArrayList<AssetRegistration> getAssetList() throws SQLException, PropertyVetoException {

		AssetRegistration assetRegistration = new AssetRegistration();
		ArrayList<AssetRegistration> assetList = new ArrayList<AssetRegistration>();
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT ar.Asset_Id,");
		queryString.append(" ar.Asset_Tag,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name,");
		queryString.append(" sup.Supplier,");
		queryString.append(" ar.Product,");
		queryString.append(" ar.Vesrion_Model,");
		queryString.append(" ar.Serial_No,");
		queryString.append(" ar.Cost,");
		queryString.append(" ar.Product_Key,");
		queryString.append(" ar.Processor,");
		queryString.append(" ar.Ram,");
		queryString.append(" ar.Hard_Disk,");
		queryString.append(" ar.Warranty,");
		queryString.append(" ar.Warranty_Coverage,");
		queryString.append(" ar.Accidential_Damage,");
		queryString.append(" ar.Asset_Owner,");
		queryString.append(" lm.Location,");
		queryString.append(" ar.Asset_Available_Status");
		queryString.append(" FROM Asset_Category_Master AS ac");
		queryString.append(" INNER JOIN Asset_Registration AS ar");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc");
		queryString.append(" INNER JOIN Asset_Supplier_Master AS sup ");
		queryString.append(" INNER JOIN location_master AS lm");
		queryString.append(" WHERE ac.Asset_Cat_Id = ar.Asset_Cat_Id");
		queryString.append(" AND sc.Sub_Cat_Id = ar.Sub_Cat_Id");
		queryString.append(" AND sup.Supplier_Id = ar.Supplier_Id");
		queryString.append(" AND lm.Location_Id = ar.Location_Id");
		/* queryString.append(" AND ar.Deleted_By is NULL"); */

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			rs1 = stmt.executeQuery(queryString.toString());
			log.info("query===" + queryString.toString());
			while (rs1.next()) {
				assetRegistration = new AssetRegistration();
				assetRegistration.setAssetId(rs1.getInt(1));
				assetRegistration.setAssetTag(rs1.getString(2));
				assetRegistration.setAssetCatName(rs1.getString(3));
				assetRegistration.setSubCatName(rs1.getString(4));
				assetRegistration.setSupplier(rs1.getString(5));
				assetRegistration.setProduct(rs1.getString(6));
				assetRegistration.setVersionModel(rs1.getString(7));
				assetRegistration.setSerialNo(rs1.getString(8));
				assetRegistration.setCost(rs1.getInt(9));
				assetRegistration.setProductKey(rs1.getString(10));
				assetRegistration.setProcessor(rs1.getString(11));
				assetRegistration.setRam(rs1.getString(12));
				assetRegistration.setHardDisk(rs1.getString(13));
				assetRegistration.setWarranty(rs1.getString(14));
				assetRegistration.setWarrantyCoverage(rs1.getString(15));
				assetRegistration.setAccidentialDamage(rs1.getString(16));
				assetRegistration.setAssetOwner(rs1.getString(17));
				assetRegistration.setLocation(rs1.getString(18));
				assetRegistration.setAssetAvailableStatus(rs1.getString(19));
				/* assetRegistration.setChargerSerialNo(rs1.getString(20)); */
				assetList.add(assetRegistration);

			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs1.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}

		return assetList;
	}

	public boolean addAssets(int assetCatId, int subCatId, int supplierId,AssetRegistration asset, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addAssets = false;
		try {
			StringBuffer queryString = new StringBuffer();

			queryString.append(" INSERT into Asset_Registration(");
			queryString.append(" Asset_Tag,");
			queryString.append(" Asset_Cat_Id,");
			queryString.append(" Sub_Cat_Id,");
			queryString.append(" Supplier_Id,");
			queryString.append(" Product, ");
			queryString.append(" Vesrion_Model,");
			queryString.append(" Serial_No,");
			queryString.append(" Cost,");
			queryString.append(" Product_Key,");
			queryString.append(" Processor,");
			queryString.append(" Ram,");
			queryString.append(" Hard_Disk,");
			queryString.append(" Warranty,");
			queryString.append(" Warranty_Coverage,");
			queryString.append(" Accidential_Damage,");
			queryString.append(" Asset_Owner,");
			queryString.append(" Location_Id,");
			queryString.append(" Asset_Available_Status,");
			queryString.append(" Created_By,");
			queryString.append(" Created_Date)");
			queryString.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
			queryString.append(adminId + ",");
			queryString.append("CURRENT_TIMESTAMP())");

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, asset.getAssetTag());
			stmt.setInt(2, asset.getAssetCatId());
			stmt.setInt(3, asset.getSubCatId());
			stmt.setInt(4, asset.getSupplierId());
			stmt.setString(5, asset.getProduct());
			stmt.setString(6, asset.getVersionModel());
			stmt.setString(7, asset.getSerialNo());
			stmt.setInt(8, asset.getCost());
			stmt.setString(9, asset.getProductKey());
			stmt.setString(10, asset.getProcessor());
			stmt.setString(11, asset.getRam());
			stmt.setString(12, asset.getHardDisk());
			stmt.setString(13, asset.getWarranty());
			stmt.setString(14, asset.getWarrantyCoverage());
			stmt.setString(15, asset.getAccidentialDamage());
			stmt.setString(16, asset.getAssetOwner());
			stmt.setInt(17, asset.getLocationId());
			stmt.setString(18, asset.getAssetAvailableStatus());
			/* stmt.setString(19, asset.getChargerSerialNo()); */
			asset.setCreatedBy(adminId);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addAssets = true;
				log.info("A new asset was inserted successfully!");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addAssets;
	}

	public boolean deleteAsset(int assetId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteAsset = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("delete from Asset_Registration where Asset_Id =");
			queryString.append(assetId);

			stmt = connection.prepareStatement(queryString.toString());
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteAsset = true;
				log.info("assetcategory deleted successfully");
			}
		} catch (Exception e) {
			log.error("got error while deleting asset ", e);
		}

		finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteAsset;

	}

	public boolean updateAssetDetails(int assetId, AssetRegistration assetRegister, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateAssetDetails = false;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Asset_Registration ");
			queryString.append(" SET Product ='");
			queryString.append(assetRegister.getProduct() + "'");
			queryString.append(" , Vesrion_Model ='");
			queryString.append(assetRegister.getVersionModel() + "'");
			queryString.append(" , Serial_No='");
			queryString.append(assetRegister.getSerialNo() + "'");
			queryString.append(" , Cost= ");
			queryString.append(assetRegister.getCost());
			queryString.append(" , Product_Key='");
			queryString.append(assetRegister.getProductKey() + "'");
			queryString.append(" , Asset_Owner='");
			queryString.append(assetRegister.getAssetOwner() + "'");
			
			/*
			 * queryString.append(" , charger_sr_no='");
			 * queryString.append(assetRegister.getChargerSerialNo() + "'");
			 */
			 
			queryString.append(" WHERE Asset_Id = ");
			queryString.append(assetRegister.getAssetId());

			stmt = connection.prepareStatement(queryString.toString());
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateAssetDetails = true;
				log.info("updated successfully ...");

			}
		} catch (NumberFormatException e) {
			log.error("got error while updating asset details ...");
		} finally {
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return updateAssetDetails;

	}

	public Boolean checkAssetTagDup(String assetTag) throws SQLException, PropertyVetoException {

		boolean assetTagFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM  Asset_Registration ");
		queryString.append(" WHERE asset_tag = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, assetTag);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					assetTagFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
			
		}
		return assetTagFlag;
	}

}