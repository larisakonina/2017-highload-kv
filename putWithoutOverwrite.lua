wrk.method = "PUT"
count = 0


request = function()
   local path = "/v0/entity?id="..count
   local body = ""

   for i= 1, 1000 do
   body = body .. math.random(1000,2000)
   end

   wrk.body = body
   count  = count + 1
   return wrk.format(nil, path)
   
end


