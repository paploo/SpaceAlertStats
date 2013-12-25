class String
  def to_sec
    m,s = split(':').map(&:to_i)
    m*60 + s
  end
end

class Array
  def to_secs_h
    each_with_object({}) {|s,h| h[s] = s.to_sec}
  end
end

times = %w{7:30 8:10 8:50 9:40}

times.to_secs_h.each do |k,v|
  puts "#{k.to_s.rjust(5)} => #{v.to_s.rjust(3)}"
end
