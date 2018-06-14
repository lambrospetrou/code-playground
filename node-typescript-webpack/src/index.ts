interface User {
  name: string
}

console.info('imported')

const print = (u: User) => console.info(u.name)

export const handler = () => print({ name: 'Lambros' })
