package com.example.track.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class DemoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public DemoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

//    public Crime getCrime(){
//        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
//        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
//        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
//        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
//
//        Crime crime = new Crime(UUID.fromString(uuidString));
//        crime.setDate(new Date(date));
//        crime.setTitle(title);
//        crime.setSolved(isSolved==1?true:false);
//
//        return crime;
//    }
}
