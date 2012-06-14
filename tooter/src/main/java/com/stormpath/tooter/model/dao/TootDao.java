package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Toot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/12/12
 * Time: 9:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TootDao {

    List<Toot> getTootsByUserId(int custId) throws Exception;

    Toot saveToot(Toot toot) throws Exception;

    void removeTootById(Integer tootId) throws Exception;
}
