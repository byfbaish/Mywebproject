DROP INDEX IDX_MOCK_CONTROL_1 ON MOCK_CONTROL ;
DROP TABLE MOCK_CONTROL;

CREATE TABLE mysql.MOCK_CONTROL (
  -- レコード識別子 (自動生成)
  ID                               BIGINT          NOT NULL AUTO_INCREMENT, 
  -- バージョン
  VERSION                          BIGINT          NOT NULL DEFAULT 0,
  -- 最終更新時刻
  LAST_UPDATED                     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  -- HTTPリクエスト・メソッド
  METHOD                           VARCHAR(8)      NOT NULL,
  -- リソース・パス
  RESOURCE                         VARCHAR(128)    NOT NULL,
  -- HTTPリクエスト・ヘッダー（ケース番号と紐づく項目）
  TRACEID                          VARCHAR(128),
  -- HTTPリクエスト・ヘッダー（同じケース番号で複数回呼出に異なる応答が必要な場合のみ個別指定、上記以外は"000000"を指定）
  TRANSACTIONSEQNO                 CHAR(6),
  -- HTTPリクエスト・パラメーター (NULLはHTTPリクエスト・メソッド+リソース・パスでのデフォルト設定、空文字はパラメーターなし)
  PARAMETERS                       LONGBLOB,
  -- レスポンス・ステータス・コード
  RESPONSE_STATUS                  SMALLINT        NOT NULL DEFAULT 200,
  -- レスポンス・ボディ
  RESPONSE_JSON                    VARCHAR(1024),
  -- ロケーション (HTTPレスポンスのロケーション・ヘッダーに設定される)
  LOCATION                         VARCHAR(512),
  -- 前処理遅延時間(ミリ秒)
  PRE_PROCESS_TIME                 INTEGER         NOT NULL DEFAULT 0,
  -- サービス処理遅延時間(ミリ秒)
  SERVICE_PROCESS_TIME             INTEGER         NOT NULL DEFAULT 0,
  -- 後処理遅延時間(ミリ秒)
  POST_PROCESS_TIME                INTEGER         NOT NULL DEFAULT 0,
  -- プライマリー・キー
  PRIMARY KEY(ID) 
)
;

--
-- ユニーク索引:IX_MOCK_CONTROL_01
-- FinTechAPIスタブ制御レコードの論理ユニーク・キー
--
CREATE UNIQUE INDEX IDX_MOCK_CONTROL_1 ON MOCK_CONTROL (
  METHOD                           ASC,
  RESOURCE                         ASC,
  TRACEID                          ASC,
  TRANSACTIONSEQNO                 ASC,
  PARAMETERS                       ASC
);