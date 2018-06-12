package dao;

import models.CatItems;
import models.Item;
import models.SmallAnimalItems;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oCatItemsDao implements CatItemsDao{
    private final Sql2o sql2o;
    public Sql2oCatItemsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(CatItems catItems) {
        String sql = "INSERT INTO items (type, priceInCents, discountAsPercentage, description) VALUES (:type, :priceInCents, :discountAsPercentage, :description)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(catItems)
                    .executeUpdate()
                    .getKey();
            catItems.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<CatItems> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items WHERE type = 'cat'")
                    .executeAndFetch(CatItems.class);
        }
    }

    @Override
    public CatItems findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM items WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(CatItems.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from items WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from items WHERE type ='cat'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

}
