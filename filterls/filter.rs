use std::io;
use std::io::prelude::*;
use std::io::BufReader;

fn is_valid(line: &String) -> bool {
	let v: Vec<&str> = line.split(' ').collect();
	if v.len() > 1 {
		match v[1].trim().parse::<u64>() {
			Ok(num) => return num > 10,
			Err(_) => return false
		}
	}
	return false;
}

fn main() {
	let reader = BufReader::new(io::stdin());

	for line in reader.lines() {
		let l = line.unwrap();
		if is_valid(&l) {
	    	println!("{}", l);
		}
	}
}
