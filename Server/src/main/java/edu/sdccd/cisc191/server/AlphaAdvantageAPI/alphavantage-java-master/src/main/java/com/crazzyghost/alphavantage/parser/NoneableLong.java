package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JsonQualifier
public @interface NoneableLong { }
