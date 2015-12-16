/**
 * Copyright 2015 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.apereo.openlrs.storage.kinesis;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.apereo.openlrs.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;

/**
 * @author ggilbert
 *
 */
@Configuration
@Profile("aws")
public class KinesisConfig {
  private static Logger log = Logger.getLogger(KinesisConfig.class);

  //May only need on local environment
  @Value("${aws.es.stream}") private String stream;

  private String url;

  @Bean
  public AmazonKinesisClient kinesisClient() {
    /* 
     * Note we expect that the api key and secret
     * are available as system parameters either:
     * Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY
     * Java System Properties - aws.accessKeyId and aws.secretKey
     */
    AmazonKinesisClient kinesisClient = new AmazonKinesisClient();
    kinesisClient.setEndpoint("kinesis.us-west-2.amazonaws.com", "kinesis", "us-west-2");
    return kinesisClient;
  }
  
  @Bean(name="AWS_KINESIS_STREAM")
  public String kinesisStreamName() {
    return stream;
  }
  
  @Bean(name="AWS_KINESIS_URL")
  public String kinesisStreamUrl() {
    return url;
  }
}
