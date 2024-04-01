# Altria
## Introduction
Altria is an anime character who loves foods so much that it seems like her soul is out of body when she sees foods. Like most of us , it's a very tough thing to decide what to eat. And that is what Altria solves.Follow a few steps below and let Altria make a easy choice for you .
## Preparation
1. Pull this repository to your machine and load it as maven project (JDK 17 is required).
2. Install ElasticSearch and config relevant configuration to applicaiton.yml in root directory of project.Just make sure that ElasticSearch account has permission to create index and access to this index.
3. Run ElasticSearch and  AltriaApplication.
4. Initialize ElasticSearch data via calling POST /foodTruck/init
## Features
Before introduction , let's make it clear about requesting parameters.
We have four parameters below.

- distanceInKM
- longitude
- latitude
- fuzzySearchMenu

Let's define The first three parameters as geography information.

The parameter distanceInKM means results would be shown only if trucks are within this distance.

The parameter fuzzySearchMenu means what you want to eat.

All features below have been tested  both in unit test and local environment.

### Get a random choice within specific distance
GET /foodTruck/nearbyChoice/random

If you're struggling with what to eat , just have a try. In this interface ,geography information is required.
### Search trucks providing certain menu within specific distance
GET /foodTruck/nearbyChoices

In this interface, the parameter fuzzySearchMenu is requires while geography information is optional.
#Architecture
All the searching are based on ElasticSearch. To improve performance , only those fields need to be searched will be stored in ElasticSearch.After searching we get a location id list,and pull complete data from somewhere else.
Considering that trucks data won't be changed frequently, so that somewhere is Redis. 

For this demo , we didn't use Redis actually but a Hashmap for convenience of coding.Please just consider this map  as real redis.
But we create an abstract class for redis cache  to make sure that data will be lazy-load from something like Mysql after expiration.By the way, Mysql in this demo is also not a real one but a csv File due to the same reason.

## Updates in future
- Reconstruct  in DDD way
- Isolate data of different country or city
- Replace with real Redis and mysql

