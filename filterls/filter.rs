use std::io;
use std::io::prelude::*;
use std::io::BufReader;
use std::io::BufWriter;

fn is_valid(line: &String) -> bool {
	let v: Vec<&str> = line.splitn(3, ' ').collect();
	if v.len() > 1 {
		match v[1].trim().parse::<u64>() {
			Ok(num) => return num > 10,
			_ => return false
		}
	}
	return false;
}

fn main() {
	let reader = BufReader::new(io::stdin());
	let mut writer = BufWriter::new(io::stdout());

	for line in reader.lines() {
		let l = line.unwrap();
		if is_valid(&l) {
	    	writeln!(writer, "{}", l).expect("Unable to write to output");
		}
	}
}

