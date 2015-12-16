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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apereo.openlrs.model.OpenLRSEntity;
import org.apereo.openlrs.storage.TierOneStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

/**
 * @author ggilbert
 *
 */
@Component("KinesisTierOneStorage")
@Profile("aws")
public class KinesisTierOneStorage implements TierOneStorage<OpenLRSEntity> {
  private static Logger log = Logger.getLogger(KinesisTierOneStorage.class);
  @Autowired private AmazonKinesisClient kinesisClient;
  @Autowired @Qualifier("AWS_KINESIS_STREAM") private String streamName;
  private String partitionKey = "TENANT_ID";
  
  @Override
  public OpenLRSEntity save(OpenLRSEntity entity) {
    PutRecordRequest putRecordRequest = new PutRecordRequest();
    putRecordRequest.setStreamName(streamName);
    putRecordRequest.setPartitionKey(partitionKey);
    String myData = entity.toJSON();
    putRecordRequest.setData(ByteBuffer.wrap(myData.getBytes()));
    PutRecordResult result = kinesisClient.putRecord(putRecordRequest);
    log.debug("Successfully putrecord, partition key : " + putRecordRequest.getPartitionKey() 
    + ", ShardID : " + result.getShardId() + "Sequence Number: "+result.getSequenceNumber());
    return entity;
  }

  //TODO this is untested!
  @Override
  public List<OpenLRSEntity> saveAll(Collection<OpenLRSEntity> entities) {
    PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
    putRecordsRequest.setStreamName(streamName);
    putRecordsRequest.setRecords(createPutRecords(entities));
    PutRecordsResult result = kinesisClient.putRecords(putRecordsRequest);
    log.debug("Successfully putrecords, FaileRecordCount : " + result.getFailedRecordCount());
    return (List<OpenLRSEntity>) entities;
  }
  
  public Collection<PutRecordsRequestEntry> createPutRecords(Collection<OpenLRSEntity> entities) {
      List<PutRecordsRequestEntry> records = new ArrayList<PutRecordsRequestEntry>();
      
      for(OpenLRSEntity entity: entities){
          PutRecordsRequestEntry requestEntry = new PutRecordsRequestEntry();
          requestEntry.withExplicitHashKey(null);
          requestEntry.withData(ByteBuffer.wrap(entity.toJSON().getBytes()));
          requestEntry.withPartitionKey(partitionKey);
          records.add(requestEntry);
      }
      return records;
  }

}
