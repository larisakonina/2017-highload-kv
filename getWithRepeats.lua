wrk.method = "GET"
count = 0


request = function()
  local path = "/v0/entity?id="..count
  count = (count + 1) % 1000
  return wrk.format(nil, path)
  
end



