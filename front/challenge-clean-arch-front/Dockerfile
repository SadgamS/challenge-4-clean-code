# Stage 1: build
FROM node:22-alpine AS build
WORKDIR /app

# Only copy package manifests first to leverage Docker cache and avoid copying node_modules
COPY package.json package-lock.json* ./

# Install deps (prefer npm ci if you have package-lock.json)
RUN npm ci --silent

# Copy source (will not include node_modules because of .dockerignore)
COPY . .

# Build (assume Angular build script already configured)
RUN npm run build --if-present

# Stage 2: serve with nginx
FROM nginx:stable-alpine AS prod
# copy custom nginx config if present
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/challenge-clean-arch-front/browser/ /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]