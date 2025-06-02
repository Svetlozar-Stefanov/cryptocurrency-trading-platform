FROM node:22.14-alpine

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

#Copy whole project
COPY . .

RUN npm run build

CMD ["npm", "run", "dev", "--", "--host"]