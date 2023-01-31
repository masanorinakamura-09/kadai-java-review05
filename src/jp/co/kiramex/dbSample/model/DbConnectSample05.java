package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con=null;
        PreparedStatement spstmt=null;
        PreparedStatement ipstmt=null;

        ResultSet rs=null;

        // 1. ドライバのクラスをJava上で読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


        // 2. DBと接続する
             con=DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrueval=true",
                    "root",
                    "Singen110209"
            );

        // 4. DBとやりとりする窓口（Statementオブジェクト）の作成

             String selectSql = "SELECT * FROM city where CountryCode = ?";
             spstmt=con.prepareStatement(selectSql);

             System.out.print("CountryCodeを入力してください > ");
             String str1 = keyIn();

             spstmt.setString(1,str1);

        // 5, 6. Select文の実行と結果を格納／代入


             rs = spstmt.executeQuery();


        // 7. 結果を表示する
             System.out.println("更新前============");
             while (rs.next()) {
                 // Name列の値を取得
                 String name = rs.getString("Name");
                 // CountryCode列の値を取得
                 String countryCode = rs.getString("CountryCode");
                 // District列の値を取得
                 String district = rs.getString("District");
                 // Population列の値を取得
                 int population = rs.getInt("Population");
                 // 取得した値を表示
                 System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);
             }

             System.out.println("更新処理実行========");
             String insertSql = "INSERT INTO city (Name,CountryCode,District,Population) VALUES ('Rafah',?,'Rafah',?)";
             ipstmt = con.prepareStatement(insertSql);

             // 更新するPopulationを入力
             System.out.print("Populationを数字で入力してください > ");
             int num1 = keyInNum();

             // 入力されたPopulationとCountryCodeをPreparedStatementオブジェクトにセット
             ipstmt.setString(1, str1);
             ipstmt.setInt(2, num1);

             // update処理の実行および更新された行数を取得
             int count = ipstmt.executeUpdate();
             System.out.println("更新行数：" + count);

             rs.close();
             System.out.println("更新後============");
             rs=spstmt.executeQuery();
             while (rs.next()) {
                 // Name列の値を取得
                 String name = rs.getString("Name");
                 // CountryCode列の値を取得
                 String countryCode = rs.getString("CountryCode");
                 // District列の値を取得
                 String district = rs.getString("District");
                 // Population列の値を取得
                 int population = rs.getInt("Population");
                 // 取得した値を表示
                 System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);
             }

        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }finally {
        // 8. 接続を閉じる
         if(rs!=null){
             try {
                 rs.close();
             }catch(SQLException e) {
                 System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                 e.printStackTrace();
              }
            }
         if(ipstmt!=null){
             try {
                 ipstmt.close();
             }catch(SQLException e) {
                 System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                 e.printStackTrace();
             }

         if(spstmt!=null){
             try {
                 spstmt.close();
             }catch(SQLException e) {
                 System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                 e.printStackTrace();
             }
         }
         if(con!=null) {
            try{
                con.close();
            }catch(SQLException e) {
                System.err.println("データ切断時にエラーが発生しました。");
                e.printStackTrace();
            }
            }
         }
        }


    }
        private static String keyIn() {
            String line = null;
            try {
                BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
                line = key.readLine();
            } catch (IOException e) {

            }
            return line;
        }

        /*
         * キーボードから入力された値をintで返す 引数：なし 戻り値：int
         */
        private static int keyInNum() {
            int result = 0;
            try {
                result = Integer.parseInt(keyIn());
            } catch (NumberFormatException e) {
            }
            return result;
        }
}
