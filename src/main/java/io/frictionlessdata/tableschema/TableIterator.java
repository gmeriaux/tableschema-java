package io.frictionlessdata.tableschema;

import io.frictionlessdata.tableschema.exceptions.ConstraintsException;
import io.frictionlessdata.tableschema.exceptions.InvalidCastException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * 
 */
public class TableIterator<T> {
    
    private String[] headers = null;
    private Schema schema = null;
    private Iterator<String[]> iter = null;
    private boolean keyed = false;
    private boolean extended = false; 
    private boolean cast = true;
    private boolean relations = false;
    private int index = 0;
    
    public TableIterator(Table table){
        this.init(table);
        this.headers = table.getHeaders();
        this.schema = table.getSchema();
        this.iter = table.getDataSource().iterator();
    }
    
    public TableIterator(Table table, boolean keyed){
        this.init(table);
        this.headers = table.getHeaders();
        this.schema = table.getSchema();
        this.iter = table.getDataSource().iterator();
        this.keyed = keyed;
    }
    
    public TableIterator(Table table, boolean keyed, boolean extended){
        this.init(table);
        this.headers = table.getHeaders();
        this.schema = table.getSchema();
        this.iter = table.getDataSource().iterator();
        this.keyed = keyed;
        this.extended = extended;
    }
    
    public TableIterator(Table table, boolean keyed, boolean extended, boolean cast){
        this.init(table);
        this.headers = table.getHeaders();
        this.schema = table.getSchema();
        this.iter = table.getDataSource().iterator();
        this.keyed = keyed;
        this.extended = extended;
        this.cast = cast;
    }
    
    public TableIterator(Table table, boolean keyed, boolean extended, boolean cast, boolean relations){
        this.init(table);
        this.keyed = keyed;
        this.extended = extended;
        this.cast = cast;
        this.relations = relations;
    }
    
    private void init(Table table){
        this.headers = table.getHeaders();
        this.schema = table.getSchema();
        this.iter = table.getDataSource().iterator();
    }
    
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public <T> T next(){
        String[] row = this.iter.next();
        
        Map<String, Object> keyedRow = new HashMap();
        Object[] extendedRow = new Object[3];
        Object[] castRow = new Object[row.length];
        
        // If there's a schema, attempt to cast the row.
        if(this.schema != null){
            try{
                for(int i=0; i<row.length; i++){
                    Field field = this.schema.getFields().get(i);
                    Object val = field.castValue(row[i], true);

                    if(!extended && keyed){
                        keyedRow.put(this.headers[i], val);
                    }else{
                        castRow[i] = val;
                    } 
                }
                
                if(extended){
                    extendedRow = new Object[]{index, this.headers, castRow}; 
                    index++;
                }
            
            }catch(InvalidCastException | ConstraintsException e){
                // The row data types do not match schema definition.
                // Or the row values do not respect the Constraint rules.
                // Do noting and string with String[] typed row.
                extendedRow = null;
                keyedRow = null;
                castRow = null;     
            }
            
        }else{
            extendedRow = null;
            keyedRow = null;
            castRow = null;
            
            return (T)row;
        }
        
        if(extended){
            return (T)extendedRow; 
            
        }else if(keyed && !extended){
            return (T)keyedRow;
            
        }else if(!keyed && !extended){
            return (T)castRow;
            
        }else{
            return (T)row;
        }
    }

}