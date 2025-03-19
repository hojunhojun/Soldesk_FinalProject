package com.ung.recipetoyou.market;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Meta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Repository
public interface MarketMongoRepo extends MongoRepository<MarketMongoDBData, String>{
	MarketMongoDBData findByRtymid(String rtymid);
	long countByRtymttContainingIgnoreCase(String query);
	
	//정확도 검색
	@Query(value = "{ $and: [ { 'rtym_querykey': { $regex: ?0, $options: 'i' }, 'rtym_cate1':'식품' }, { 'rtym_price': { $gte: 5000, $lte: 1000000 } } ] }", sort = "{ 'rtym_price': 1 }")
	@Meta(allowDiskUse = true)
	Page<MarketMongoDBData> findByRtymqkContainingAndPriceBetweenOrderByPriceAsc(String query, Pageable pageable);
	
//	//카테고리 검색
//	@Query("{ $and: [ { 'rtym_querykey': { $regex: ?0, $options: 'i' } }, { 'rtym_cate3': { $regex: ?0, $options: 'i' } }, { 'rtym_price': { $lte: 1000000 } } ] }")
//	Page<MarketMongoDBData> findByRtymqkAndRtymct3ContainingAndPriceLessThanEqual(String query, Pageable pageable);
	
	//카테고리 검색 qk and ct3 or ct2 option add
	@Query("{ $and: [ { 'rtym_querykey': { $regex: ?0, $options: 'i' } }, { $or: [ { 'rtym_cate3': { $regex: ?0, $options: 'i' } }, { 'rtym_cate2': { $regex: ?0, $options: 'i' } } ] }, { 'rtym_price': { $lte: 1000000 } } ] }")
	Page<MarketMongoDBData> findByRtymqkAndRtymct3OrRtymct2ContainingAndPriceLessThanEqual(String query, Pageable pageable);


	//가격 오름차순
	@Query("{ $and: [ { 'rtym_querykey': { $regex: ?0, $options: 'i' } }, { 'rtym_price': { $gte: 1000, $lte:1000000 } } ] }")
	Page<MarketMongoDBData> findByRtymqkContainingAndPriceGreaterThanEqualOrderByRtymprceAsc(String query, Pageable pageable);
	
	//가격 내림차순
	@Query("{ $and: [ { 'rtym_querykey': { $regex: ?0, $options: 'i' } }, { 'rtym_price': { $gte: 50000, $lte: 1000000 } } ] }")
	Page<MarketMongoDBData> findByRtymqkContainingAndPriceBetweenOrderByRtymprceDesc(String query, Pageable pageable);
	
	//DB 등록순
	Page<MarketMongoDBData> findByRtymqkContainingIgnoreCaseOrderByRtymidDesc(String query, Pageable pageable);
	
	@Query(value = "{}", fields = "{ 'rtym_title': 1, '_id': 0 }")
    List<String> findAllRtymTitles();

    default List<String> findCleanRtymTitles() {
        return findAllRtymTitles().stream()
            .map(this::extractTitleFromJson)
            .map(this::cleanTitle)
            .collect(Collectors.toList());
    }

    private String extractTitleFromJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonString);
            return node.get("rtym_title").asText();
        } catch (Exception e) {
            return "";
        }
    }

    private String cleanTitle(String title) {
        String cleanedTitle = title.replaceAll("<[^>]*>", "");
        cleanedTitle = cleanedTitle.replaceAll("&amp;", "&")
                                   .replaceAll("&lt;", "<")
                                   .replaceAll("&gt;", ">")
                                   .replaceAll("&quot;", "\"")
                                   .replaceAll("&#39;", "'");
        cleanedTitle = cleanedTitle.replaceAll("\\s+", " ").trim();
        return cleanedTitle;
    }
}
