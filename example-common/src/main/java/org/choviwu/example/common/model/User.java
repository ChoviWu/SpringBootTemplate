package org.choviwu.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    @NonNull
    private Integer id;


}
