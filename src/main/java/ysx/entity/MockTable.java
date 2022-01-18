package ysx.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "MOCK_CONTROL")
public class MockTable {

    /** 識別子 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 更新バージョン */
    @Version
    private long version;

    /** 最終更新時刻 */
    @Column(name = "LAST_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    /** HTTPリクエスト・メソッド */
    @Column(name = "METHOD")
    private String method;

    /** リソース・パス */
    @Column(name = "RESOURCE")
    private String resource;

    /** HTTPリクエスト・トレースID */
    @Column(name = "TRACEID")
    private String traceID;

    /** リソース・取引通番 */
    @Column(name = "TRANSACTIONSEQNO")
    private String transactionSeqNo;

    /** リクエスト・パラメーター */
    @Column(name = "PARAMETERS")
    private String parameters;

    /** ステータス・コード */
    @Column(name = "RESPONSE_STATUS")
    private int responseStatus;

    /** レスポンス情報(JSON) */
    @Column(name = "RESPONSE_JSON")
    private String responseJson;

    /** ロケーション(HTTPレスポンスのLocationヘッダーに設定) */
    @Column(name = "LOCATION")
    private String location;

    /** 前処理遅延時間(ミリ秒) */
    @Column(name = "PRE_PROCESS_TIME")
    private long preProcessTime;

    /** サービス処理遅延時間(ミリ秒) */
    @Column(name = "SERVICE_PROCESS_TIME")
    private long serviceProcessTime;

    /** 後処理遅延時間(ミリ秒) */
    @Column(name = "POST_PROCESS_TIME")
    private long postProcessTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPreProcessTime() {
        return preProcessTime;
    }

    public void setPreProcessTime(long preProcessTime) {
        this.preProcessTime = preProcessTime;
    }

    public long getServiceProcessTime() {
        return serviceProcessTime;
    }

    public void setServiceProcessTime(long serviceProcessTime) {
        this.serviceProcessTime = serviceProcessTime;
    }

    public long getPostProcessTime() {
        return postProcessTime;
    }

    public void setPostProcessTime(long postProcessTime) {
        this.postProcessTime = postProcessTime;
    }

    @PrePersist
    @PreUpdate
    private void now() {
        setLastUpdated(new Date(System.currentTimeMillis()));
    }
}
