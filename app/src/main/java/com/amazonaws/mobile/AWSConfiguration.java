//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.17
//
package com.amazonaws.mobile;

import com.amazonaws.mobilehelper.config.AWSMobileHelperConfiguration;
import com.amazonaws.regions.Regions;

/**
 * This class defines constants for the developer's resource
 * identifiers and API keys. This configuration should not
 * be shared or posted to any public source code repository.
 */
public class AWSConfiguration {
// AWS MobileHub user agent string
public static final String AWS_MOBILEHUB_USER_AGENT = "MobileHub ca8c846e-9193-4462-a23a-cf71caaacffd aws-my-sample-app-android-v0.17";
// AMAZON COGNITO
public static final Regions AMAZON_COGNITO_REGION = Regions.fromName("us-east-1");
public static final String AMAZON_COGNITO_IDENTITY_POOL_ID = "us-east-1:08595abf-75b7-44c9-a610-4f1e4df4f370";
public static final String AMAZON_COGNITO_USER_POOL_ID = "us-east-1_GJXqxLyxq";
public static final String AMAZON_COGNITO_USER_POOL_CLIENT_ID = "55ajoemltg7567dmgibvfkpnh0";
public static final String AMAZON_COGNITO_USER_POOL_CLIENT_SECRET = "16mr6aa4c19fk27pso1h5bmrug1s4hc1p12f9979eqs30brc230i";

private static final AWSMobileHelperConfiguration helperConfiguration = new AWSMobileHelperConfiguration.Builder().withCognitoRegion(AMAZON_COGNITO_REGION).withCognitoIdentityPoolId(AMAZON_COGNITO_IDENTITY_POOL_ID).withCognitoUserPool(AMAZON_COGNITO_USER_POOL_ID,
    AMAZON_COGNITO_USER_POOL_CLIENT_ID, AMAZON_COGNITO_USER_POOL_CLIENT_SECRET)
    .build();
    /**
      * @return the configuration for AWSKit.
      */
public static AWSMobileHelperConfiguration getAWSMobileHelperConfiguration() {
    return helperConfiguration;
    }
}