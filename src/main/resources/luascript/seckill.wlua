local kckey=KEYS[1];
local userKey=KEYS[2];
local userid =  ARGV[1];
local userExists=redis.call("sismember", userKey, userid);
if tonumber(userExists)==1 then
    return 2;
end
local num = redis.call("get", kckey);
if num then
    if tonumber(num)<=0 then
        return 0;
    else
        redis.call("decr", kckey);
        redis.call("sadd", userKey, userid);
    end
    return 1;
else
    return -1;
end
